(ns main
  (:require [gamelib :as gamelib]))

;; Conway's Game of Life, written in ClojureScript
;; 
;; The game board is defined by using a single dimension to represent a square
;; Then given the number of generations it will compute the simulation to the nth generation
;; These "generation frames" are represented as nested vectored lists

;; This function was created with help from Gemini to aid with testing accuracy
(defn display-board [cells]
  (doseq [row cells]
    ;; 1. Map the display function over the row
    ;; 2. Join them with a space
    ;; 3. Print the resulting single string for the whole row
    (println (clojure.string/join " " (map gamelib/display row)))))

;; Change this to change the initial state
(def initial-state '([1 0] [2 1] [0 2] [1 2] [2 2]))

(def dimensions 6) ;; Change this to change the size of the square
(def cs 
  (vec 
    (for [y (range dimensions)]
      (vec 
        (for [x (range dimensions)] 
          (let [initialize? (boolean (some #{[y x]} initial-state))]
            (gamelib/init-cell x y initialize?)))))))

(def nth-gen (gamelib/run-n dimensions cs 6)) ;; run-n (size) (vectored list) (generations)
(doseq [gen nth-gen]
  (println "\n--- Generation ---")
  (display-board gen))
(println "\n**END OF EXECUTION**")
