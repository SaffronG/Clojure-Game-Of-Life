(ns main
  (:require [gamelib :as gamelib]))

;; Conway's Game of Life, written in ClojureScript
;; 
;; The game board is defined by using a single dimension to represent a square
;; Then given the number of generations it will compute the simulation to the nth generation
;; These "generation frames" are represented as nested vectored lists

(def cs (for [x (range 3) y (range 3)] (gamelib/init-cell x y false)))
(def dcs (map gamelib/display cs))
(println dcs)
