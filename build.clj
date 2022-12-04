(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]))

(def lib 'io.github.seulvagem/aoc22)
(def version "0.4.0")
(def main 'seulvagem.aoc22)

(defn test "Run the tests." [opts]
  (bb/run-tests opts))

(defn ci "Run the CI pipeline of tests (and build the uberjar)." [opts]
  (-> opts
      (assoc :lib lib :version version :main main)
      #_(bb/run-tests)
      (bb/clean)
      (bb/uber)))
