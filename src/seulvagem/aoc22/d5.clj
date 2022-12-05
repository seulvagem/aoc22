(ns seulvagem.aoc22.d5
  (:require [seulvagem.aoc22.util :as u]
            [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.pprint :as pp]
            [clojure.set :as set]))

(defn parse-crates-line [s]
  ;; rest to skip the first [, mapping blank character to nil for readability and semantics
  (->> s rest (take-nth 4) (map #(if (= % \ )
                                       nil
                                       %))))

(defn get-rf-create-stacks [stack-ids]
  (println stack-ids)
  (let [initial-stacks (mapv (constantly '()) (conj stack-ids 0))]
    (println initial-stacks)
    (fn
      ([] initial-stacks)
      ([stacks] stacks)
      ([stacks [stack-id crate]]
       (update stacks stack-id conj crate)))))

(defn parse-stack-ids [s]
  (->> s (re-seq #"\d+" ) (map edn/read-string)))

(defn create-stacks [input-str]
  (let [[ids-line & crates-lines] (-> input-str (str/split #"\n") rseq)
        stack-ids (parse-stack-ids ids-line)

        xf (comp (map parse-crates-line)
                 (map #(map-indexed vector %))
                 (map #(map (fn [[i c]] [(inc i) c]) %)) ;; inc index to match stack-id
                 cat
                 (filter #(get % 1))
                 ) ;; flatten id,crate tuples

        rf-create-stacks (get-rf-create-stacks stack-ids)
        ]
    (transduce xf rf-create-stacks crates-lines)))

(defn move-crate [stacks [from to]]
  ;; (println "MOVE" from to)
  (let [from-stack (stacks from)
        to-stack (stacks to)
        crate (peek from-stack)]
    (when-not crate (println "no crate bruh"))
    (-> stacks
        (update from pop)
        (update to conj crate))))

(defn take-and-reorder-crates [qty stack]
  (into '() (take qty stack)))

(defn move-crates-9001 [stacks {:keys [qty from to]}]
  ;; (println "MOVE" from to)
  ;; (println :stacks stacks)
  ;; (println :move-keys qty from to)
  (let [from-stack (stacks from)
        to-stack   (stacks to)
        crates     (take-and-reorder-crates qty from-stack)]
    (-> stacks
        (update from (partial drop qty))
        (update to (partial reduce conj) crates))))

(defn step->moves [step-str]
  (let [digits (re-seq #"\d+" step-str)
        [qty & stack-ids] (map edn/read-string digits)

        ]

    (repeat qty (vec stack-ids))
    ))

(defn step->move-map [step-str]
  (let [digits            (re-seq #"\d+" step-str)
        [qty from to] (map edn/read-string digits)]
    {:qty qty, :from from, :to to}))

(defn main [simple?]
  (let [input (u/get-input 5 simple?)
        [stacks-creation-input steps-input] (str/split input #"\R{2}")
        initial-stacks (create-stacks stacks-creation-input)
        steps (str/split-lines steps-input)

        xf1 (comp (map step->moves)
                 cat)
        rf-move-crates (completing move-crate)
        final-stacks1 (transduce xf1 rf-move-crates initial-stacks steps)

        xf2 (comp (map step->move-map))
        rf-move-crates-9001 (completing move-crates-9001)
        final-stacks2 (transduce xf2 rf-move-crates-9001 initial-stacks steps)

        get-top-crates #(->> % (do (pp/pprint %) %) (map first) (apply str))

        res1 (get-top-crates final-stacks1)
        res2 (get-top-crates final-stacks2)
        ]
    [res1 res2]))

#_(main false)
