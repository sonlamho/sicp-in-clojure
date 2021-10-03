(ns sicp-in-clojure.ex1-11)

(defn f-recursive [n]
  (if (< n 3)
    n
    (+ (f-recursive (- n 1))
       (* 2 (f-recursive (- n 2)))
       (* 3 (f-recursive (- n 3))))))

(defn f-iter [n]
  (letfn [(iter [m x3 x2 x1]
            (cond (== m 0) x3
                  :else (iter (- m 1)
                              x2
                              x1
                              (+ x1 (* 2 x2) (* 3 x3)))))]
    (iter n 0N 1N 2N)))
