(ns lib)

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
(defn alive? [cell] 
  (cell 'alive?))
(defn check-neighbor [cell]
  (cell 'check-neighbor))
(defn evolve [cell]
  (cell 'evolve))
(defn get-xy [cell]
  (cell 'get-xy))
(defn display [cell]
  (cell 'display))

(defn xy=? [cell this-x this-y]
  (let [x (0 (get-xy cell)) y (1 (get-xy cell))]
    (cond 
      (= (= x this-x) (= y this-y)) true
      :else false)))

;; functions to operate on structs
(defn next-gen? [nb cells]
  (reduce + (for [[x y] nb] ;; Haskell-like destructuring
    (if (alive? (get-in cells [y x]))) ;; -> cells[y][x].is_alive();
      1 ;; T
      0))) ;; F

(def cs (for [x (range 3) y (range 3)] (init-cell x y false)))
(def dcs (map display cs))
(println dcs)
