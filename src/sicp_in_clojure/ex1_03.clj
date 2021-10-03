(ns sicp-in-clojure.ex1-03)

(defn square [x] (* x x))

(defn sum-square-larger [a b c]
  (- (apply + (map square [a b c]))
     (square (min a b c))))
