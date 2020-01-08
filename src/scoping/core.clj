(ns scoping.core)
(require '[clj-time.core :as t])

(t/after? one two)

(defn -main [& args]
  (println "Hello World"))

;; priorities: holidays/travel, oncall, inday, buffers, tasks
;; We want 6 story points of buffer after every 18 story points of work

(defstruct item :type :story-points :date :description)

(defstruct sprint :start :end :number :tasks) ;; start and end are dates

(def sprints (list
              (struct sprint (t/date-time 2020 1 6) (t/date-time 2020 1 20) 1 (list))
              (struct sprint (t/date-time 2020 1 20) (t/date-time 2020 2 3) 2 (list))
              (struct sprint (t/date-time 2020 2 3) (t/date-time 2020 2 17) 3 (list))
              (struct sprint (t/date-time 2020 2 17) (t/date-time 2020 3 2) 4 (list))
              (struct sprint (t/date-time 2020 3 2) (t/date-time 2020 3 3) 5 (list))
              (struct sprint (t/date-time 2020 3 30) (t/date-time 2020 4 13) 6 (list))
              (struct sprint (t/date-time 2020 4 13) (t/date-time 2020 4 27) 7 (list))
              (struct sprint (t/date-time 2020 4 27) (t/date-time 2020 5 11) 8 (list))
              (struct sprint (t/date-time 2020 5 11) (t/date-time 2020 5 25) 9 (list))
              (struct sprint (t/date-time 2020 5 25) (t/date-time 2020 6 8) 10 (list))
              (struct sprint (t/date-time 2020 6 8) (t/date-time 2020 6 22) 11 (list))
              (struct sprint (t/date-time 2020 6 22) (t/date-time 2020 7 6) 12 (list))
              ))

(def holidays (list
               (struct item :holiday 1 (t/date-time 2020 1 20) "holiday")
               (struct item :holiday 1 (t/date-time 2020 2 17) "holiday")
               (struct item :holiday 1 (t/date-time 2020 3 25) "holiday")
               (struct item :holiday 1 (t/date-time 2020 7 3) "holiday")
               (struct item :holiday 1 (t/date-time 2020 7 4) "holiday")
               (struct item :holiday 1 (t/date-time 2020 7 5) "holiday")
               (struct item :holiday 1 (t/date-time 2020 7 6) "holiday")
               (struct item :holiday 1 (t/date-time 2020 7 7) "holiday")
               (struct item :holiday 1 (t/date-time 2020 7 8) "holiday")
               ))

(def indays (list
             (struct item :inday 1 (t/date-time 2020 1 24) "inday")
             (struct item :inday 1 (t/date-time 2020 2 21) "inday")
             (struct item :inday 1 (t/date-time 2020 3 20) "inday")
             (struct item :inday 1 (t/date-time 2020 4 17) "inday")
             (struct item :inday 1 (t/date-time 2020 5 15) "inday")
             (struct item :inday 1 (t/date-time 2020 6 19) "inday")
             (struct item :inday 1 (t/date-time 2020 7 24) "inday")
             (struct item :inday 1 (t/date-time 2020 8 21) "inday")
             (struct item :inday 1 (t/date-time 2020 9 11) "inday")
             (struct item :inday 1 (t/date-time 2020 10 16) "inday")
             (struct item :inday 1 (t/date-time 2020 11 13) "inday")
             (struct item :inday 1 (t/date-time 2020 12 11) "inday")
             ))

(def tasks
  (struct item :work 4 nil "Get API and Security Signoff")
  (struct item :work 6 nil "Add New Offline Spark Job")
  (struct item :work 6 nil "Update TopN")
  (struct item :work 6 nil "Load test Pinot")
  (struct item :work 1 nil "Add Data to pinot")
  (struct item :work 3 nil "Verify data in pinot")
  (struct item :work 1 nil "Add new pivot to TRB commons")
  (struct item :work 6 nil "Add pivot logic and tests to TRB")
  (struct item :work 5 nil "Add LIX to TRB")
  (struct item :work 3 nil "Verify API requests")
  (struct item :work 2 nil "Load test API")
  )

