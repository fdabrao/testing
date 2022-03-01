(ns app.scramble-test
  (:require [clojure.test :refer :all]
            [app.scramble :as scramble]))

(deftest test-clean-string-only-letter
  (is (= "relatedstring" (scramble/keep-only-lowercase-letters "reAAEEF11233--1..lated---223999455...-st3343ring"))))

(deftest test-string-array-into-string-exists
  (is (= true (scramble/scramble-char? "aabbc" ["a"] true)))
  (is (= true (scramble/scramble-char? "aabbc" ["a" "b"] true)))
  (is (= true (scramble/scramble-char? "aabbc" ["a" "b" "c"] true)))
  (is (= false (scramble/scramble-char? "aabbc" ["a" "b" "d"] true)))
  (is (= false (scramble/scramble-char? "aabbc" ["a" "d"] false)))
  (is (= false (scramble/scramble-char? "aabbc" ["d"] false))))

(deftest test-scramble-for-portion-char
  (is (= true (scramble/scramble? "rekqodlw" "world")))
  (is (= true (scramble/scramble? "cedewaraaossoqqyt" "codewars")))  
  (is (= false (scramble/scramble? "katas" "steak"))))

(deftest test-scramble-for-cleaning-not-included-digit-or-punctuation
  (is (= true (scramble/scramble? "cedewaraaossoq122AADD..12333-qyt" "code122AADD..12333---112..wars")))
  (is (= true (scramble/scramble? "cedewaraaossoqqyt" "code122AADD..12333---112..wars")))
  (is (= true (scramble/scramble? "cedewaraAADD..12333---112..aossoqqyt" "codewars"))))