(ns sicp-in-clojure.ex3
  (:require [sicp-in-clojure.ch3] 
            [sicp-in-clojure.ch1]
            ))

; --------------------------------------------------

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
(= 150 (B 40))
)

; --------------------------------------------------

(defn make-monitored
  "Exercise 3.2"
  [fun]
  (let
    [counter (atom 0)
     monitored 
      (fn ([x] 
            (cond 
              (= x 'how-many-calls?) @counter
              (= x 'reset-count) (reset! counter 0)
              :else
                (do (swap! counter inc)
                    (fun x))))
          ([x & xs] 
            (swap! counter inc)
            (apply fun x xs))
          ([] 
            (swap! counter inc)
            (fun)))]
    monitored))

(defn sqrt [x] (Math/sqrt x))
(comment 
"Test code for Ex3.2"
(def s (make-monitored sqrt))
(= (s 'how-many-calls?) 0)
(s 49)
(println (s 'how-many-calls?))
(s 100)
(println (s 'how-many-calls?))
(s 'reset-count)
(println (s 'how-many-calls?))
(take 77 (map s (range)))
(println (s 'how-many-calls?))
)

; --------------------------------------------------
