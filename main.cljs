(ns main
  (:require [gamelib :as gamelib]))

;; Conway's Game of Life, written in ClojureScript
;; 
;; The game board is defined by using a single dimension to represent a square
;; Then given the number of generations it will compute the simulation to the nth generation
;; These "generation frames" are represented as nested vectored lists

;; This function was created with help from Gemini to aid with testing accuracy
(defn display-board [cells]
  (doseq [row cells]           ;; Iterate through each row vector
    (doseq [cell row]          ;; Iterate through each cell closure in the row
      (print (gamelib/display cell))) ;; Print the cell string and a separator
    (println)))                ;; Move to a new line after each row

(def initial-state '([1 2] [1 0] [2 2] [0 0])) ;; Change this to change the initial state

(def dimensions 3) ;; Change this to change the size of the square
(def cs 
  (vec 
    (for [y (range dimensions)]
      (vec 
        (for [x (range dimensions)] 
          (let [initialize? (boolean (some #{[y x]} initial-state))]
            (gamelib/init-cell x y initialize?)))))))

(display-board cs)
(println "\n**END OF EXECUTION**")
