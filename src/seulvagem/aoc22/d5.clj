(ns seulvagem.aoc22.d5
  (:require [seulvagem.aoc22.util :as u]
            [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.pprint :as pp]
            [clojure.set :as set]))

(defn parse-crates-line [s]
  (->> s rest (take-nth 4)))

(defn get-rf-create-cargo [stack-ids]
  (let [initial-stacks (reduce #(assoc %1 %2 '()) {} stack-ids)]
    (fn
      ([] initial-stacks)
      ([stacks] [stack-ids stacks])
      ([stacks [stack-id crate]]
       (update stacks stack-id conj crate)))))

(defn digits-seq [s]
  (re-seq #"\d+" s))

(defn create-cargo [input-str]
  (let [[ids-line & crates-lines] (-> input-str (str/split #"\n") rseq)
        stack-ids                 (digits-seq ids-line)
        stacks-count              (count stack-ids)
        ix->id                    (zipmap (range) stack-ids)

        xf (comp (map parse-crates-line)
                 (mapcat #(map-indexed (fn [ix crate]
                                         [(rem ix stacks-count)
                                          (if (= crate \ )
                                            nil
                                            crate)]) %))
                 (filter #(get % 1))
                 (map #(update % 0 ix->id)))]

    (transduce xf (get-rf-create-cargo stack-ids) crates-lines)))

(defn move-crate [stacks [from to]]
  (let [from-stack (stacks from)
        to-stack (stacks to)
        crate (peek from-stack)]
    (-> stacks
        (update from pop)
        (update to conj crate))))

(defn take-and-reorder-crates [qty stack]
  (into '() (take qty stack)))

(defn move-crates-9001 [stacks {:keys [qty from to]}]
  (let [from-stack (stacks from)
        to-stack   (stacks to)
        crates     (take-and-reorder-crates qty from-stack)]
    (-> stacks
        (update from (partial drop qty))
        (update to (partial reduce conj) crates))))

(defn step->moves [[qty & stack-ids]]
  (repeat (edn/read-string qty) (vec stack-ids)))

(defn step->move-map [[qty from to]]
  {:qty (edn/read-string qty), :from from, :to to})

(defn get-top-crates [stack-ids stacks]
  (->>  stack-ids
        (map (fn [id]
               (-> id stacks first)))
        (apply str)))

(defn main [simple?]
  (let [input (u/get-input 5 simple?)
        [stacks-creation-input steps-input] (str/split input #"\R{2}")
        [stack-ids initial-stacks] (create-cargo stacks-creation-input)
        step-strs (str/split-lines steps-input)

        xf-parse-step-str (map digits-seq)

        xf1 (comp xf-parse-step-str
                  (mapcat step->moves))
        rf-move-crates (completing move-crate)
        final-stacks1 (transduce xf1 rf-move-crates initial-stacks step-strs)

        xf2 (comp xf-parse-step-str
                  (map step->move-map))
        rf-move-crates-9001 (completing move-crates-9001)
        final-stacks2 (transduce xf2 rf-move-crates-9001 initial-stacks step-strs)

        res1 (get-top-crates stack-ids final-stacks1)
        res2 (get-top-crates stack-ids final-stacks2)]
    [res1 res2]))

#_(main false)
