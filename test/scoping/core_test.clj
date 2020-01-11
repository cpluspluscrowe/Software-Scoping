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

(deftest fill-sprints-test
  (testing "Test filling sprints"
    (is (=
         (fill-sprints (list sprint-example) (list task-example))
         (list filled)))))

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
   (struct block nil nil 1 (struct item :work 6 nil "Big Task") 0)
   (struct block nil nil 2 (struct item :work 1 nil "Big Task") 5)
   )
  )

(deftest fill-sprints-larger-test
  (testing "Test filling sprints with a large task"
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

(def task
    (struct item :work 6 nil "Task")
  )

(deftest add-buffer-to-tasks-test
  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-buffer-to-tasks (list task) 16)
         (list (struct item :buffer 6 nil "Buffer") task)
         ))))

(deftest add-buffer-to-tasks-test2
  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-buffer-to-tasks (list task task) 15)
         (list task (struct item :buffer 6 nil "Buffer") task)
         ))))

(deftest add-buffer-to-tasks-test3
  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-buffer-to-tasks (list task task task task) 0)
         (list task task task (struct item :buffer 6 nil "Buffer") task)
         ))))

(def holiday (struct item :holiday 1 (t/date-time 2020 2 2) "holiday"))


(deftest add-holiday-to-sprints-test
  (let ([given-sprints (list
                        (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 5) 2 (list) 6)
                        )
         expected-sprints (list
                           (struct block (t/date-time 2020 2 1) (t/date-time 2020 2 5) 2 (list holiday) 6)
                           )
         ]
        )

  (testing "Test adding buffer to tasks"
    (println "Main test")
    (is (=
         (add-holiday-to-sprints given-sprints holiday)
         expected-sprints
         ))))
  )



