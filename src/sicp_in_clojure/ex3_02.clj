(ns sicp-in-clojure.ex3-02)

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

