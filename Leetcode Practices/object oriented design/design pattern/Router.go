package main

import (
	"fmt"
	"net/http"
)

// Router 定义一个路由器结构体
type Router struct {
	handlers map[string]http.HandlerFunc
}

// NewRouter 创建一个新的路由器实例
func NewRouter() *Router {
	return &Router{
		handlers: make(map[string]http.HandlerFunc),
	}
}

// HandleFunc 向路由器中添加新的处理函数
func (r *Router) HandleFunc(path string, handler http.HandlerFunc) {
	r.handlers[path] = handler
}

// 实现 http.Handler 接口，用于处理请求
func (r *Router) ServeHTTP(w http.ResponseWriter, req *http.Request) {
	// 从 handlers 字段中查找当前请求路径对应的处理函数
	handler, ok := r.handlers[req.URL.Path]
	if !ok {
		// 如果找不到，则返回 404 错误
		http.NotFound(w, req)
		return
	}

	// 否则，直接调用相应的处理函数来处理当前请求
	handler(w, req)
}

// 处理 "/" 路径的请求
func handlerHome(w http.ResponseWriter, r *http.Request) {
	if _, err := fmt.Fprint(w, "Welcome to the home page!"); err != nil {
		return
	}
}

// 处理 "/about" 路径的请求
func handlerAbout(w http.ResponseWriter, r *http.Request) {
	if _, err := fmt.Fprint(w, "Learn more about us!"); err != nil {
		return
	}
}

// 处理 "/contact" 路径的请求
func handlerContact(w http.ResponseWriter, r *http.Request) {
	if _, err := fmt.Fprint(w, "Get in touch with us!"); err != nil {
		return
	}
}

func main() {
	// 创建一个新的路由器实例
	router := NewRouter()

	// 将不同的 URL 路径与相应的处理函数进行映射
	router.HandleFunc("/", handlerHome)
	router.HandleFunc("/about", handlerAbout)
	router.HandleFunc("/contact", handlerContact)

	// 启动服务器，并将路由器实例作为参数传入
	err := http.ListenAndServe(":8080", router)
	if err != nil {
		fmt.Println("Failed to start server:", err)
	}
}
