(ns app.scramble
  (:require [clojure.string :as st]))

(defn keep-only-lowercase-letters [value]
  (apply str (filter #(Character/isLowerCase %) value)))

(defn break-to-array-string [value]
  (clojure.string/split value #""))

(defn scramble-char? [source target result]
  (if (or (empty? target) (= false result))
    result
    (if (.contains source (first target))
      (recur source (rest target) true)
      (recur source (rest target) false))))

(defn scramble? [source target]
  (let [source-clean (keep-only-lowercase-letters source)
        target-clean (keep-only-lowercase-letters target)]
    (scramble-char? source-clean (break-to-array-string target-clean) true)))
