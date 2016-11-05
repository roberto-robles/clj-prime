(ns clj-prime.core-test
  (:require [clojure.test :refer :all]
            [clj-prime.core :refer :all]))

(deftest prime-seq-test
  (testing "prime? predicate test"
    (is (boolean (prime? [2 3] 5)))
    (is (not (boolean (prime? [2 3 5 7] 10)))))

  (testing "Prime seq is a seq-able object"
    (is (seq (prime-seq))))
  
  (testing "Default length test"
    (is (= 10 (count (prime-seq)))))

  (testing "Custom length test"
    (is (= 100 (count (prime-seq 100)))))

  (testing "Prime numbers test"
    (let [s (prime-seq 100)]

      (is (= 2 (first s)))
      (is (= 3 (second s)))
      (is (= 5 (nth s 2)))
      (is (= 7 (nth s 3)))))


  (let [test-vector (prime-seq 1600)
        test-seq    (prime-seq 1500)]

    (testing "If length is greater than 1500 we go to the vec approach test"
      (is (not (vector? test-seq)))
      (is (vector? test-vector)))

    (testing "Vector and sequence contains valid prime numbers test"
      (is (= (seq (take 500 test-vector))
             (take 500 test-seq))))))
