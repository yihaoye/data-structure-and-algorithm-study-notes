package main

import (
	"log"
	"net/http"
)

// 定义一个记录请求信息的中间件
func middlewareLogging(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		log.Printf("%s %s", r.Method, r.URL.Path)
		next.ServeHTTP(w, r)
	})
}

// 定义一个检查 API 密钥的中间件
func middlewareApiKey(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		apiKey := r.Header.Get("X-API-Key")
		if apiKey != "my-secret-key" {
			http.Error(w, "Invalid API key", http.StatusUnauthorized)
			return
		}
		next.ServeHTTP(w, r)
	})
}

// 定义一个处理请求的处理函数
func handlerImpl(w http.ResponseWriter, r *http.Request) {
	if _, err := w.Write([]byte("Hello, World!")); err != nil {
		return
	}
}

func main() {
	// 创建一个处理器链
	handler := middlewareApiKey(middlewareLogging(http.HandlerFunc(handlerImpl)))

	// 启动 HTTP 服务器
	if err := http.ListenAndServe(":8080", handler); err != nil {
		panic("Server Error")
	}
}
