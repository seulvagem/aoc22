(ns seulvagem.aoc22.d4
  (:require [seulvagem.aoc22.util :as u]
            [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.set :as set]))

(defn range-inc [start end]
  (range start (inc end)))

(def section-str-pairs->set
  (comp set
        #(apply range-inc %)
        #(mapv edn/read-string %)
        #(str/split % #"-")
        ))

(defn section-str->set [section-str]
  (->> (str/split section-str #",")
       (mapv section-str-pairs->set)))

(defn contained? [sets]
  (or (apply set/subset? sets)
      (apply set/superset? sets)))

(defn intersects? [sets]
  (seq (apply set/intersection sets)))

(defn main [simple?]
  (let [lines           (u/get-split-input 4 simple?)
        xf-section->set (map section-str->set)

        xf1             (comp xf-section->set (filter contained?))
        xf2             (comp xf-section->set (filter intersects?))

        count-res #(count (sequence % lines))

        res1 (count-res xf1)
        res2 (count-res xf2)]

    [res1 res2]))

#_(main false)
