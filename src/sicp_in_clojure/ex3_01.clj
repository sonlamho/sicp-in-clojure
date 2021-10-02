(ns sicp-in-clojure.ex3-01)

(defn make-accumulator
  "Exercise 3.1"
  [init]
  (let [v (atom init)]
    (fn [amount] (swap! v #(+ % amount)))))

(comment
  "Test code for Ex3.1"
  (def A (make-accumulator 5))
  (def B (make-accumulator 100))
  (= 15 (A 10))
  (= 16 (A 1))
  (= 110 (B 10))
  (= 26 (A 10))
  (= 150 (B 40)))

