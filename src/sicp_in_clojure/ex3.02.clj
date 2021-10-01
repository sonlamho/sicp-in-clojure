(ns sicp-in-clojure.ex3)

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
  (println (s 'how-many-calls?)))

