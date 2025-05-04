package main

import (
	"database/sql"
	"fmt"
	"log"
	"time"

	_ "github.com/lib/pq" // PostgreSQL 驱动，虽然没有直接看到它的函数调用。这是因为 Go 的 database/sql 是通过驱动注册机制来调用底层驱动的，只需要导入它，它就会自动注册到 sql 包中。这行的 _ 是 Go 中的 “匿名导入”，用于执行包的 init() 函数，但不直接使用包中的任何标识符。在这个驱动中，init() 会自动把 PostgreSQL 驱动注册进 Go 的 database/sql 框架
)

type Conn struct {
	ID int
}

type Pool struct {
	ch chan *Conn
}

func NewPool(size int) *Pool {
	ch := make(chan *Conn, size)
	for i := 0; i < size; i++ {
		ch <- &Conn{ID: i} // 预填资源
	}
	return &Pool{ch: ch}
}

// 获取资源（阻塞）
func (p *Pool) Get() *Conn {
	return <-p.ch
}

// 归还资源
func (p *Pool) Put(conn *Conn) {
	p.ch <- conn
}

func main1() {
	pool := NewPool(2)
	a := pool.Get()
	b := pool.Get()
	fmt.Println("Get: %d, %d", a.ID, b.ID)

	go func() {
		time.Sleep(time.Second)
		pool.Put(a)
	}()
	c := pool.Get()
	fmt.Println("Get: %d", c.ID)
}

func main2() {
	// 初始化连接池
	db, err := sql.Open("postgres", "host=localhost port=5432 user=postgres password=yourpass dbname=testdb sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	// 配置连接池参数
	db.SetMaxOpenConns(10)           // 最大连接数
	db.SetMaxIdleConns(5)            // 最大空闲连接数
	db.SetConnMaxLifetime(time.Hour) // 连接最大生命周期

	// 使用连接
	var now time.Time
	err = db.QueryRow("SELECT NOW()").Scan(&now)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Current time from DB:", now)
}
