(ns seulvagem.aoc22.util
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            ))

(defn get-input [day simple?]
  (let [path (str "resources/d" day (when simple? "-simple") ".txt")]
    (slurp path)))

(def get-split-input (comp str/split-lines get-input))

(def parse-number edn/read-string)
