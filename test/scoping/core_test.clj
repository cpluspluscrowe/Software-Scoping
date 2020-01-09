(ns scoping.core-test
  (:require [clojure.test :refer :all]
            [scoping.core :refer :all]))

(require '[clj-time.core :as t])

(deftest a-test
  (testing "Example"
    (is (= 0 0))))

(def sprint-example
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (list) 6))

(def task-example
  (struct item :work 3 nil "Small Task"))

(def big-task
  (struct item :work 7 nil "Big Task"))

(def filled
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 task-example 3))

(deftest fill-sprints-test
  (testing "Test filling sprints"
    (is (=
         (fill-sprints (list sprint-example) (list task-example))
         filled))))

(deftest does-task-fit-in-sprint-test
  (testing "Test filling sprints"
    (is (=
         (does-task-fit-in-sprint sprint-example task-example)
         true))
    (is (=
         (does-task-fit-in-sprint sprint-example big-task)
         false))
    (is (=
         (does-task-fit-in-sprint sprint-example   (struct item :work 6 nil "Big Task"))


         true))))




;;(deftest fill-sprints-larger-test
;;  (testing "Test filling sprints"
;;    (is (=
;;         (fill-sprints (list sprint-example sprint-example) (list big-task))
;;         filled
;;         ))))



