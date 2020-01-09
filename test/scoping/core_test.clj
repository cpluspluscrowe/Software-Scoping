(ns scoping.core-test
  (:require [clojure.test :refer :all]
            [scoping.core :refer :all]))

(require '[clj-time.core :as t])

(def sprint-example
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (list) 6))

(def sprint-example2
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 2 (list) 6))


(def task-example
  (struct item :work 3 nil "Small Task"))

(def big-task
  (struct item :work 7 nil "Big Task")
  )

(def filled
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 task-example 3))

(def filled2
  (list
   (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (struct item :work 6 nil "Big Task") 0)
   (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (struct item :work 1 nil "Big Task") 5)
   )
  )

(deftest fill-sprints-test
  (testing "Test filling sprints"
    (is (=
         (fill-sprints (list sprint-example) (list task-example))
         (list filled)
         ))))

(deftest fill-sprints-test-too-many-sprints
  (testing "Test filling sprints when there are extra sprints"
    (is (=
         (fill-sprints (list sprint-example sprint-example) (list task-example))
         (list filled sprint-example)
         ))))


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

(deftest update-larger-task-test
  (testing "Test filling sprints"
    (is (=
         (update-larger-task sprint-example big-task)
         (list
          (struct item :work 6 nil "Big Task")
          (struct item :work 1 nil

                  "Big Task"))))))

(deftest fill-sprints-larger-test
  (testing "Test filling sprints with a large task"
    (println "Main test")
    (is (=
         (fill-sprints (list sprint-example sprint-example2) (list big-task))
         (list filled2)
         ))))


(deftest a-test
  (testing "Example"
    (is (= 0 0))))


