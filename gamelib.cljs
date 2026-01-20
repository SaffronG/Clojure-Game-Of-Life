(ns gamelib)

;; cell as closure (original)
(defn init-cell [x y state]
  (fn [predicate]
    (cond
      (= predicate 'alive?) state
      (= predicate 'check-neighbor)
      (for [x-off [-1 0 1]
            y-off [-1 0 1]]
        [(+ x x-off) (+ y y-off)])
      (= predicate 'evolve) ()
      (= predicate 'get-xy) [x y]
      (= predicate 'display) (str x " " y " " state)
      :else nil)))

;; accessors (original)
(defn alive? [cell] (cell 'alive?))
(defn check-neighbor [cell] (cell 'check-neighbor))
(defn evolve [cell] (cell 'evolve))
(defn get-xy [cell] (cell 'get-xy))
(defn display [cell] (cell 'display))

;; unused helper (original, kept)
(defn xy=? [cell this-x this-y]
  (let [x (0 (get-xy cell))
        y (1 (get-xy cell))]
    (cond
      (= (= x this-x) (= y this-y)) true
      :else false)))

;; neighbor alive counter (original idea, slightly cleaned)
(defn next-gen? [nb cells]
  (reduce +
    (for [[x y] nb]
      (if (alive? (get-in cells [y x]))
        1
        0))))

;; small helper added (NOT finishing logic)
(defn in-bounds? [w h [x y]]
  (and (<= 0 x) (< x w)
       (<= 0 y) (< y h)))

;; TODO
(defn survives? [w h cells cell]
  :TODO)

;; TODO 
(defn next-generation [w h cells]
  :TODO)

;; TODO
(defn run-n [w h cells n]
  :TODO)
