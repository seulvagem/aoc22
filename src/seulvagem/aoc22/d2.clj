(ns seulvagem.aoc22.d2
  (:require [clojure.string :as str]
            [seulvagem.aoc22.util :as u]
            ))

(def letter->shape
  {"X" :rock
   "Y" :paper
   "Z" :scissors
   "A" :rock
   "B" :paper
   "C" :scissors})

(def letter->result
  {"X" :lose
   "Y" :draw
   "Z" :win})

(def result->points
  {:draw 3
   :lose 0
   :win  6})

(def points-map
  {:rock     {:shape 1, :result {:rock :draw, :paper :lose, :scissors :win}}
   :paper    {:shape 2, :result {:rock :win, :paper :draw, :scissors :lose}}
   :scissors {:shape 3, :result {:rock :lose, :paper :win, :scissors :draw}}})

(defn get-shape-points [{shape :we}]
  (-> shape points-map :shape))

(defn get-result-points [{:keys [we they]}]
  (-> we points-map :result they result->points))

(defn day2p1 [simple?]
  (let [rounds-str            (u/get-split-input 2 simple?)
        xf-split-round        (map #(str/split % #" "))
        xf-parse-round-shapes (map (partial mapv letter->shape))
        xf-format-round       (map (fn [[opponent me]]
                                     {:they opponent, :we me}))
        xf-get-points         (map (juxt get-shape-points get-result-points))
        xf-sum-round          (map #(reduce + %))
        xf                    (comp xf-split-round xf-parse-round-shapes xf-format-round xf-get-points xf-sum-round)
        res1                  (transduce xf + rounds-str)]
    res1))

(def points-map2
  {:rock     {:shape 1, :result {:draw :rock, :win :paper, :lose :scissors}}
   :paper    {:shape 2, :result {:lose :rock, :draw :paper, :win :scissors}}
   :scissors {:shape 3, :result {:win :rock :lose :paper, :draw :scissors}}})

(defn get-result-points2 [{:keys [result]}]
  (-> result->points result))

(defn get-shape-points2 [{:keys [result they]}]
  (-> points-map2 they :result result points-map :shape))

(defn day2p2 [simple?]
  (let [rounds-str             (u/get-split-input 2 simple?)
        xf-split-round         (map #(str/split % #" "))
        xf-parse-round-letters (map #(-> %
                                         (update 0 letter->shape)
                                         (update 1 letter->result)))
        xf-format-round        (map (fn [[opponent me]]
                                      {:they opponent, :result me}))
        xf-get-points          (map (juxt get-shape-points2 get-result-points2))
        xf-sum-round           (map #(reduce + %))
        xf                     (comp xf-split-round xf-parse-round-letters xf-format-round  xf-get-points xf-sum-round)
        res1                   (transduce xf + rounds-str)
        ;; res1 (into [] xf rounds-str)
        ]
    res1))

(defn main [simple?]
  [(day2p1 simple?) (day2p2 simple?)])
