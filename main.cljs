(ns main
  (:require [gamelib :as gamelib]))

(def cs (for [x (range 3) y (range 3)] (gamelib/init-cell x y false)))
(def dcs (map gamelib/display cs))
(println dcs)
