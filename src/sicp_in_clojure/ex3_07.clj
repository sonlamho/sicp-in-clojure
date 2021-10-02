(ns sicp-in-clojure.ex3)

(defn make-account
  "Exercise 3.3"
  [bal password]
  (let
   [balance (atom bal)

    withdraw
    (fn [amount]
      (if (>= @balance amount)
        (swap! balance #(- % amount))
        "Insufficient funds"))

    deposit
    (fn [amount] (swap! balance #(+ % amount)))

    dispatch
    (fn [pwd m]
      (if (= pwd password)
        (cond (= m 'withdraw) withdraw
              (= m 'deposit) deposit
              :else (throw (Exception. "Unknown request")))
        (fn [& _] "Incorrect password")))]

    dispatch))

(defn make-joint
  "Exercise 3.7"
  [acc acc-pwd new-pwd]
  (fn [pwd m]
    (if (= pwd new-pwd)
      (acc acc-pwd m)
      (fn [& _] "Incorrect password"))))

(comment
  (def peter-acc (make-account 100 'open-sesame))
  ((peter-acc 'open-sesame 'deposit) 100)
  (def paul-acc
    (make-joint peter-acc 'open-sesame 'rosebud))
  ((paul-acc 'open-sesame 'withdraw) 50)  ; -> bad password
  ((paul-acc 'rosebud 'withdraw) 50)
  ((peter-acc 'open-sesame 'withdraw) 20))

