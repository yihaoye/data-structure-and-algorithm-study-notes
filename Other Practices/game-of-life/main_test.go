package main

import (
	"maps"
	"testing"
)

func TestIterate(t *testing.T) {
	tests := []struct {
		name     string
		input    map[coord]struct{}
		times    int
		expected map[coord]struct{}
	}{
		{
			name: "block is stable (still life)",
			input: map[coord]struct{}{
				{0, 0}: {}, {1, 0}: {}, {0, 1}: {}, {1, 1}: {},
			},
			times: 10,
			expected: map[coord]struct{}{
				{0, 0}: {}, {1, 0}: {}, {0, 1}: {}, {1, 1}: {},
			},
		},
		{
			name: "L-tromino fills into a block after 1 generation",
			input: map[coord]struct{}{
				{0, 0}: {}, {1, 0}: {}, {1, 1}: {},
			},
			times: 1,
			expected: map[coord]struct{}{
				{0, 0}: {}, {1, 0}: {}, {0, 1}: {}, {1, 1}: {},
			},
		},
		{
			name: "blinker oscillates with period 2",
			input: map[coord]struct{}{
				{0, 1}: {}, {1, 1}: {}, {2, 1}: {}, // horizontal
			},
			times: 2,
			expected: map[coord]struct{}{
				{0, 1}: {}, {1, 1}: {}, {2, 1}: {}, // back to horizontal after 2 gens
			},
		},
		{
			name: "lonely cell dies (underpopulation)",
			input: map[coord]struct{}{
				{0, 0}: {},
			},
			times:    1,
			expected: map[coord]struct{}{},
		},
		{
			name: "large coordinates don't overflow or crash",
			input: map[coord]struct{}{
				{0, 0}: {}, {1, 0}: {}, {1, 1}: {},
				{-2000000000000, -2000000000000}: {},
				{-2000000000001, -2000000000001}: {},
				{-2000000000000, -2000000000001}: {},
			},
			times: 1,
			expected: map[coord]struct{}{
				{0, 0}: {}, {1, 0}: {}, {0, 1}: {}, {1, 1}: {},
				{-2000000000000, -2000000000000}: {},
				{-2000000000001, -2000000000001}: {},
				{-2000000000000, -2000000000001}: {},
				{-2000000000001, -2000000000000}: {},
			},
		},
		{
			name:     "empty grid stays empty",
			input:    map[coord]struct{}{},
			times:    5,
			expected: map[coord]struct{}{},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			cells := maps.Clone(tt.input) // avoid mutating shared test data
			for range tt.times {
				iterate(cells)
			}
			if !maps.Equal(cells, tt.expected) {
				t.Errorf("iterate() = %v, want %v", cells, tt.expected)
			}
		})
	}
}
