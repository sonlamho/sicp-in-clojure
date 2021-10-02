(ns sicp-in-clojure.ex3)

(def a (atom 1))

(defn f
  "Exercise 3.8"
  [x]
  (let [currenta @a]
    (reset! a x)
    (* @a x currenta)))

(reset! a 1)
(+ (f 0) (f 1))
(reset! a 1)
(+ (f 1) (f 0))

