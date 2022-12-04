(ns seulvagem.aoc22.d3
  (:require [seulvagem.aoc22.util :as u]
            [clojure.set :as set]))

(defn char->priority
  [c]
  (let [ascii (int c)]
    (if (>= ascii 97)
      (- ascii 96)
      (- ascii (- 65 27)))))

(defn main [simple?]
  (let [rucksacks (u/get-split-input 3 simple?)

        xf-split-at-half (map #(split-at (-> % count (/ 2)) %))
        xf-strs->sets (map #(mapv set %))
        xf-intersection (map #(apply set/intersection %))
        xf-get-common-type (comp xf-intersection (map first))
        xf-priority (map char->priority)
        xf-batch-groups (partition-all 3)

        xf-get-common-priority (comp xf-strs->sets xf-get-common-type xf-priority)
        xf1 (comp xf-split-at-half xf-get-common-priority)
        xf2 (comp xf-batch-groups xf-get-common-priority)
        transduce+ #(transduce % + rucksacks)

        res1 (transduce+ xf1)
        res2 (transduce+ xf2)]

    [res1 res2]))

#_(main false)
