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
  (struct item :work 7 nil "Big Task"))

(def filled
  (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (list task-example) 3))

(deftest fill-sprints-test
  (def expected (list
                 (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (list task-example) 3)))

  (testing "Test filling sprints"
    (is (=
         (fill-sprints (list sprint-example) (list task-example))
         expected))))

(deftest fill-sprints-test-too-many-sprints
  (testing "Test filling sprints when there are extra sprints"
    (is (=
         (fill-sprints (list sprint-example sprint-example) (list task-example))
         (list filled sprint-example)))))

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

(def filled2
  (list
   (struct block nil nil 1 (list (struct item :work 6 nil "Big Task")) 0)
   (struct block nil nil 2 (list (struct item :work 1 nil "Big Task")) 5)))

(deftest fill-sprints-larger-test
  (testing "Test filling sprints with a large task"
    (is (=
         (fill-sprints
          (list
           (struct block nil nil 1 (list) 6)
           (struct block nil nil 2 (list) 6))
          (list big-task))
         filled2))))

(def task
  (struct item :work 6 nil "Task"))

(deftest add-buffer-to-tasks-test
  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-buffer-to-tasks (list task) 16)
         (list (struct item :buffer 6 nil "Buffer") task)))))

(deftest add-buffer-to-tasks-test2
  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-buffer-to-tasks (list task task) 15)
         (list task (struct item :buffer 6 nil "Buffer") task)))))

(deftest add-buffer-to-tasks-test3
  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-buffer-to-tasks (list task task task task) 0)
         (list task task task (struct item :buffer 6 nil "Buffer") task)))))

;; (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
;; (deftest add-holiday-to-sprints-test
;;   (let ([given-sprints (list
;;                         (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 5) 2 (list) 6)
;;                         )
;;          expected-sprints (list
;;                            (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 5) 2 (list holiday) 6)
;;                            )
;;          ]
;;         )

;;   (testing "Test adding buffer to tasks"
;;     (println "Main test")
;;     (is (=
;;          (add-holiday-to-sprints given-sprints holiday)
;;          expected-sprints
;;          ))))
;;   )


(deftest is-date-within-sprint-test
  (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
  (def sprint-with-date
    (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 5) 1 (list) 6))
  (testing "Check if date is within the sprint"
    (is (=
         (is-item-within-sprint holiday sprint-with-date)))))

(deftest is-date-within-sprint-test2
  (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
  (def sprint-with-date
    (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 2) 1 (list) 6))
  (testing "Check if date is within the sprint"
    (is (=
         (is-item-within-sprint holiday sprint-with-date)))))

(deftest is-date-within-sprint-test3
  (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
  (def sprint-with-date
    (struct block (t/date-time 2020 2 2) (t/date-time 2020 2 3) 1 (list) 6))
  (testing "Check if date is within the sprint"
    (is (=
         (is-item-within-sprint holiday sprint-with-date)))))

(deftest is-date-within-sprint-test4
  (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
  (def sprint-with-date
    (struct block (t/date-time 2020 3 2) (t/date-time 2020 3 3) 1 (list) 6))
  (testing "Check if date is within the sprint"
    (is (=
         (not (is-item-within-sprint holiday sprint-with-date))))))

(deftest add-item-to-sprints-test
  (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
  (def blocks-2 (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 20) 1 (list) 6))
  (def blocks (list
               blocks-2))
  (def expected (list
                 (add-task-to-sprint blocks-2 holiday)))

  (testing "Check if date is within the sprint"
    (is (=
         (add-item-to-sprints blocks holiday)
         expected))))

(deftest add-item-to-sprints-test2
  (def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))
  (def blocks-1 (struct block (t/date-time 2020 1 1) (t/date-time 2020 1 20) 1 (list) 6))
  (def blocks-2 (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 2) 1 (list) 6))
  (def blocks-3 (struct block (t/date-time 2020 3 1) (t/date-time 2020 3 20) 1 (list) 6))
  (def blocks (list
               blocks-1
               blocks-2
               blocks-3))
  (def expected (list
                 blocks-1
                 (add-task-to-sprint blocks-2 holiday)
                 blocks-3))

  (testing "Check if date is within the sprint"
    (is (=
         (add-item-to-sprints blocks holiday)

         expected))))

(deftest add-items-to-sprints-test
  (def items (list
              (struct item :holiday 1 (t/date-time 2020 2 2) "holiday")))

  (def blocks-2 (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 20) 1 (list) 6))
  (def blocks (list
               blocks-2))
  (def expected (list
                 (add-task-to-sprint blocks-2 (peek items))))

  (testing "Check if date is within the sprint"
    (is (=
         (add-items-to-sprints items blocks)
         expected))))

(deftest add-items-to-sprints-test2
  (def items (list
              (struct item :holiday 1 (t/date-time 2020 2 2) "holiday")
              (struct item :oncall 4 (t/date-time 2020 2 5) "oncall")))

  (def blocks-2 (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 20) 1 (list) 6))
  (def blocks (list
               blocks-2))
  (def expected (list
                 (add-task-to-sprint (add-task-to-sprint blocks-2 (peek items)) (peek (pop items)))))

  (testing "Check if date is within the sprint"
    (is (=
         (add-items-to-sprints items blocks)
         expected))))

(deftest add-items-to-sprints-test-multiple-holidays
  (def items (list
              (struct item :holiday 1 (t/date-time 2020 2 2) "holiday")
              (struct item :oncall 4 (t/date-time 2020 2 5) "oncall")))

  (def blocks-1 (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 4) 1 (list) 6))
  (def blocks-2 (struct block (t/date-time 2020 2 5) (t/date-time 2020 2 7) 1 (list) 6))
  (def blocks (list
               blocks-1
               blocks-2))
  (def expected (list
                 (add-task-to-sprint blocks-1 (peek items))
                 (add-task-to-sprint blocks-2 (peek (pop items)))))

  (testing "Check if date is within the sprint"
    (is (=
         (add-items-to-sprints items blocks)


         expected))))


