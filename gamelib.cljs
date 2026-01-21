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
      (= predicate 'display) (str x " " y " " state) ;; -> to-string function
      :else nil)))

;; accessors
(defn alive? [cell] (cell 'alive?))
(defn get-neighbors [cell] (cell 'get-neighbors))
(defn get-xy [cell] (cell 'get-xy))
(defn display [cell] (cell 'display))

;; initialize the simple nested vec struct for the game board
(defn init-list [dim]
  (vec (for [row (range dim)]
    (vec (for [col (range dim)]
      (init-cell col row false)))))) ;; -> [board]

;; neighbor alive counter (original idea, slightly cleaned)
;; Not null-checking -> Throws error
;; TODO add null-check
(defn count-alive [nb cells]
  (reduce +
    (for [[x y] nb]
      (if (alive? (get-in cells [y x]))
        1
        0))))

;; small helper added (NOT finishing logic)
;; TODO -> apply to count-alive function
(defn in-bounds? [w h [x y]]
  (and (<= 0 x) (< x w)
       (<= 0 y) (< y h)))

;; added function to be placed within map to check if each cell will survive to the next generation
(defn survives? [dimensions cells cell] 
    (let [nb-count (count-alive (get-neighbors cell) cells)
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
  (for [cell cells]
    (map survives? dimensions cells cell)))

;; TODO
(defn run-n [dimensions cells n]
  :TODO) ;; Run til the nth generation
