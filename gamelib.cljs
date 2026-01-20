(ns gamelib)

;; custom structs
(defn init-cell [x y state]
  (fn [predicate]
    (cond
      (= predicate 'alive?) state ;; returns T or F (etc. state)
      (= predicate 'check-neighbor) 
        (for [x-off [-1 0 1]
              y-off [-1 0 1]]
          [(+ x x-off) (+ y y-off)])
      (= predicate 'evolve) ()
      (= predicate 'get-xy) [x y]
      (= predicate 'display) (str x y state)
      :else nil)))

;; function accessors

;; cell alive? -> T or F
(defn alive? [cell]
  (cell 'alive?))

;; cell chek-neighbor -> [x-y chords ...]
(defn check-neighbor [cell]
  (cell 'check-neighbor))

;; cell evolve -> NOT IMPLEMENTED
(defn evolve [cell]
  (cell 'evolve))

;; cell get-xy -> [x y]
(defn get-xy [cell]
  (cell 'get-xy))

;; cell display -> "x y (T or F)"
(defn display [cell]
  (cell 'display))

;; no longer used, use the next-gen? function for faster lookup speeds
(defn xy=? [cell this-x this-y]
  (let [x (0 (get-xy cell)) y (1 (get-xy cell))]
    (cond 
      (= (= x this-x) (= y this-y)) true
      :else false)))

;; functions to operate on structs
(defn next-gen? [nb cells]
  (reduce + 
    (for [[x y] nb] ;; Haskell-like destructuring
      (if (alive? (get-in cells [y x])) ;; -> cells[y][x].is_alive();
        1 ;; T
        0)))) ;; F
