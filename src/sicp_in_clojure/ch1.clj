(ns sicp-in-clojure.ch1)

(defn square [x] (* x x))

(defn factorial
  [n]
  (letfn
   [(fac-iter [prod counter]
      (if (> counter n)
        prod
        (fac-iter (* counter prod)
                  (+ 1N counter))))]
    (fac-iter 1N 1N)))

(comment
  "Test code for factorial"
  (= (factorial 8) (apply * (range 1 9)))
  (= (factorial 123) (apply * (range 1N 124N)))
  (println (factorial 30))
  ; (fac-iter 1N 1N)
  )
