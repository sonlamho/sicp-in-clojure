(ns sicp-in-clojure.utils)

(defn falsy? [b] (or (= b false) (= b nil)))
(defn truthy? [b] (not (falsy? b)))

(defn all? [bools]
  (if (empty? bools)
    true
    (and (first bools) (all? (rest bools)))))
