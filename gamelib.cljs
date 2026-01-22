(ns gamelib)

;; cell as closure
(defn init-cell [x y state] ;; similar to cons
  (fn [predicate]
    (cond
      (= predicate 'alive?) state ;; -> T / F
      (= predicate 'get-neighbors)
      (vec (for [x-off [-1 0 1]
            y-off [-1 0 1]]
        [(+ x x-off) (+ y y-off)]))
        (= predicate 'get-xy) [x y] ;; -> [all neghbors]
      (= predicate 'display) (if (= true state) "#" "-") ;; -> to-string function
      :else nil)))

;; accessors
(defn alive? [cell] (cell 'alive?))
(defn get-neighbors [cell] (cell 'get-neighbors))
(defn get-xy [cell] (cell 'get-xy))
(defn display [cell] (cell 'display))

;; initialize the simple nested vec struct for the game board
;; Might need to force an eval because it is Lazy Evaluated
(defn init-list [dim]
  (vec (for [row (range dim)]
    (vec (for [col (range dim)]
      (init-cell col row false)))))) ;; -> [board]

;; neighbor alive counter (original idea, slightly cleaned)
(declare in-bounds?)
(defn count-alive [dimensions nb cells]
  (reduce +
    (for [[x y] nb]
      (if (in-bounds? dimensions [x y])
        (if (alive? (get-in cells [y x]))
          1
          0)
        0))))

;; small helper added (NOT finishing logic)
(defn in-bounds? [dimensions [x y]]
  (and (<= 0 x) (< x dimensions)
       (<= 0 y) (< y dimensions)))

;; added function to be placed within map to check if each cell will survive to the next generation
(defn survives? [dimensions cells cell] 
    (let [nb-count (count-alive dimensions (get-neighbors cell) cells)
          is-alive (alive? cell)]
      ;; define rules
      (if is-alive
        ;; alive
        (cond
          (< nb-count 2) false ;; underpopulation
          (and (>= nb-count 3) (<= nb-count 4)) true ;; nothing
          :else false ;; overpopulation
        ) 
        ;; dead
        (cond
          (= nb-count 3) true ;; reproduction
          :else false))))

(defn next-generation [dimensions cells]
  (vec (for [row cells]
    (vec (for [cell row]
      (let [[x y] (get-xy cell)
            new-state (survives? dimensions cells cell)]
        (init-cell x y new-state)))))))

(defn run-n [dimensions cells n]
  (take n (iterate #(next-generation dimensions %) cells))) ;; Run til the nth generation
