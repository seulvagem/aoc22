(ns seulvagem.aoc22.d1
  (:require [clojure.string :as str]
            [seulvagem.aoc22.util :as u]
            ))

(defn rf-top-3
  ([] )
  ([acc]
   (apply + acc))
  ([acc calorie-count]))

(defn main [simple?]
  (let [elfs-input         (-> (u/get-input 1 simple?)
                               (str/split #"\R{2}"))
        xf-split-inventory (map str/split-lines)
        xf-parse-calories  (map (fn [calories-strs]
                                  (map u/parse-number calories-strs)))
        xf-sum             (map #(reduce + %))
        xf                 (comp xf-split-inventory
                                 xf-parse-calories
                                 xf-sum)
        snack-leaderboard  (into (sorted-set-by >) xf elfs-input)
        res1               (first snack-leaderboard)
        top-3              (take 3 snack-leaderboard)
        res2               (apply + top-3)]
    [res1 res2]
    ))
