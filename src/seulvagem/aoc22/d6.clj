(ns seulvagem.aoc22.d6
  (:require [seulvagem.aoc22.util :as u]))

(defn rf-first
  ([])
  ([x] x)
  ([_ x]
   (reduced x)))

(defn get-package [input packet-size]
  (let [packets (partition-all packet-size 1 input)

        xf (comp (map set)
                 (map-indexed (fn [ix packet]
                                [(+ ix packet-size) packet]))
                 (filter (fn [[_ packet-set]]
                           (= (count packet-set) packet-size))))]

    (transduce xf rf-first packets)))

(defn main [simple?]
  (let [input   (u/get-input 6 simple?)
        get-res #(first (get-package input %))
        res1    (get-res 4)
        res2    (get-res 14)]
    [res1 res2]))

#_(main false)
