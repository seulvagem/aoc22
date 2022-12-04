(ns seulvagem.aoc22.main
  (:require [seulvagem.aoc22.d1 :as d1]
            [seulvagem.aoc22.d2 :as d2]
            [seulvagem.aoc22.d3 :as d3]
            [clojure.pprint :as pp]
            ))

(defn main
  "I don't do a whole lot."
  []
  [(d1/main false)
   (d2/main false)])

(defn -main
  [& args]
  (pp/pprint (main)))
