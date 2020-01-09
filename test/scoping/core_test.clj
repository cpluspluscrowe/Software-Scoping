(ns scoping.core-test
  (:require [clojure.test :refer :all]
            [scoping.core :refer :all]))

(require '[clj-time.core :as t])

(def sprint-example
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (list) 6)
  )

(def sprint-example2
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 2 (list) 6))

(def task-example
  (struct item :work 3 nil "Small Task"))

(def big-task
  (struct item :work 7 nil "Big Task"))

(def filled
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 task-example 3))

(def filled2
  (list
   (struct block nil nil 1 (struct item :work 6 nil "Big Task") 0)
   (struct block nil nil 2 (struct item :work 1 nil "Big Task") 5)
   )
  )

(deftest fill-sprints-larger-test
  (testing "Test filling sprints with a large task"
    (println "Main Test")
    (is (=
         (fill-sprints
          (list
           (struct block nil nil 1 (list) 6)
           (struct block nil nil 2 (list) 6)
           )
          (list big-task)
          )
         filled2
         ))
    )
  )

;;(deftest add-buffer-to-tasks-test
;;  (testing "Test adding buffer to tasks"
;;    (println "Main test")
;;    (is (=
;;         (fill-sprints (list sprint-example sprint-example2) (list big-task))
;;         (list filled2)))))


