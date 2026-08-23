package temporal_app

import "fmt"

func CreateGreeting(name string) (string, error) {
	return fmt.Sprintf("Hello, %s!", name), nil
}
