(ns gamelib)

;; cell as closure
(defn init-cell [x y state]
  (fn [predicate]
    (cond
      (= predicate 'alive?) state
      (= predicate 'get-neighbors)
      (vec (for [x-off [-1 0 1]
            y-off [-1 0 1]]
        [(+ x x-off) (+ y y-off)]))
      (= predicate 'evolve) ()
      (= predicate 'get-xy) [x y]
      (= predicate 'display) (str x " " y " " state)
      :else nil)))

;; initialize the simple nested vec struct for the game board
(defn init-list [dim]
  (vec (for [row (range dim)]
    (vec (for [col (range dim)]
      (init-cell col row false))))))

;; accessors
(defn alive? [cell] (cell 'alive?))
(defn get-neighbors [cell] (cell 'get-neighbors))
(defn evolve [cell] (cell 'evolve))
(defn get-xy [cell] (cell 'get-xy))
(defn display [cell] (cell 'display))

;; neighbor alive counter (original idea, slightly cleaned)
(defn count-alive [nb cells]
  (reduce +
    (for [[x y] nb]
      (if (alive? (get-in cells [y x]))
        1
        0))))

;; small helper added (NOT finishing logic)
(defn in-bounds? [w h [x y]]
  (and (<= 0 x) (< x w)
       (<= 0 y) (< y h)))

;; added function to be placed within map to check if each cell will survive to the next generation
(defn survives? [dimensions cells cell] 
    (let [nb-count (count-alive (get-neighbors cell) cells)
          is-alive (alive? cell)]
      ;; define rules
      (if is-alive
        (cond
          (< nb-count 2) true;; underpopulation
          (and (>= nb-count 3) (<= nb-count 4)) true ;; nothing
          :else false ;; overpopulation
        ) 
        (cond
          (= nb-count 3) true ;; reproduction
          :else false))))

;; TODO 
(defn next-generation [w h cells]
  :TODO)

;; TODO
(defn run-n [w h cells n]
  :TODO)
