(ns seulvagem.aoc22
  (:gen-class)
  (:require [seulvagem.aoc22.d1 :as d1]
            [seulvagem.aoc22.d2 :as d2]
            [seulvagem.aoc22.d3 :as d3]
            [seulvagem.aoc22.d4 :as d4]
            [clojure.edn :as edn]
            [clojure.pprint :as pp]))

(defn alldays
  "runs and returns all days result tuples"
  [{:keys [simple]
    :or   {simple false}}]
  [(d1/main simple)
   (d2/main simple)
   (d3/main simple)
   (d4/main simple)])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [parsed-args (map edn/read-string args)]
    (pp/pprint (alldays parsed-args))))
