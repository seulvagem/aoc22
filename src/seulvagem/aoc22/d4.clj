(ns seulvagem.aoc22.d4
  (:require [seulvagem.aoc22.util :as u]
            [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.set :as set]))

(defn main [simple?]
  (let [lines (u/get-split-input 4 simple?)
        xf (comp (map #(str/split % #","))
                 (map #(mapv (fn [range-str] (str/split range-str #"-")) %))
                 ;; (map #(mapv (partial mapv edn/read-string)))
                 (map #(mapv (fn [[start end]] (range (edn/read-string start) (inc (edn/read-string end)))) %))
                 (map #(mapv set %))
                 ;; (filter #(or (apply set/subset? %) (apply set/superset? %)))
                 (filter #(seq (apply set/intersection %)))
                 )

        res1 (count (into [] xf lines))
        res2 2]

    [res1 res2]))

#_(main true)
