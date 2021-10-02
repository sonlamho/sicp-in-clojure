(ns sicp-in-clojure.ex3-01)

(defn make-accumulator
  "Exercise 3.1"
  [init]
  (let [v (atom init)]
    (fn [amount] (swap! v #(+ % amount)))))
