(ns sicp-in-clojure.ex3-07)

(defn make-joint
  "Exercise 3.7"
  [acc acc-pwd new-pwd]
  (fn [pwd m]
    (if (= pwd new-pwd)
      (acc acc-pwd m)
      (fn [& _] "Incorrect password"))))

