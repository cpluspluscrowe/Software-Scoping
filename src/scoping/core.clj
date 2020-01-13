(ns scoping.core)
(require '[clj-time.core :as t])
(require '[clj-time.format :as f])

;; (t/after? one two)

;; priorities: holidays/travel, oncall, inday, buffers, tasks
;; We want 6 story points of buffer after every 18 story points of work

;; add all hoplidays in first, assume they all fit into sprints
;; then add buffers into tasks (every 18 points)
;; then add tasks to sprints

(defstruct item :type :story-points :date :description)

;; think of a block of work
(defstruct block :start :end :number :tasks :points-left) ;; start and end are dates

(def sprints (list
              (struct block (t/date-time 2020 1 6) (t/date-time 2020 1 19) 1 (list) 6)
              (struct block (t/date-time 2020 1 20) (t/date-time 2020 2 2) 2 (list) 6)
              (struct block (t/date-time 2020 2 3) (t/date-time 2020 2 16) 3 (list) 6)
              (struct block (t/date-time 2020 2 17) (t/date-time 2020 3 1) 4 (list) 6)
              (struct block (t/date-time 2020 3 2) (t/date-time 2020 3 15) 5 (list) 6)
              (struct block (t/date-time 2020 3 16) (t/date-time 2020 3 29) 6 (list) 6)
              (struct block (t/date-time 2020 3 30) (t/date-time 2020 4 12) 7 (list) 6)
              (struct block (t/date-time 2020 4 13) (t/date-time 2020 4 26) 8 (list) 6)
              (struct block (t/date-time 2020 4 27) (t/date-time 2020 5 10) 8 (list) 6)
              (struct block (t/date-time 2020 5 11) (t/date-time 2020 5 24) 9 (list) 6)
              (struct block (t/date-time 2020 5 25) (t/date-time 2020 6 7) 10 (list) 6)
              (struct block (t/date-time 2020 6 8) (t/date-time 2020 6 21) 11 (list) 6)
              (struct block (t/date-time 2020 6 22) (t/date-time 2020 7 5) 12 (list) 6)
              (struct block (t/date-time 2020 7 6) (t/date-time 2020 7 19) 13 (list) 6)
              (struct block (t/date-time 2020 7 20) (t/date-time 2020 8 2) 14 (list) 6)
              (struct block (t/date-time 2020 8 3) (t/date-time 2020 8 16) 15 (list) 6)
              (struct block (t/date-time 2020 8 17) (t/date-time 2020 8 30) 16 (list) 6)
              (struct block (t/date-time 2020 8 31) (t/date-time 2020 9 13) 17 (list) 6)
              (struct block (t/date-time 2020 9 14) (t/date-time 2020 10 11) 18 (list) 6)
              ))

(def travels (list
              (struct item :travel 2 (t/date-time 2020 1 29) "Travel Hackathon")
              (struct item :travel 2 (t/date-time 2020 4 23) "Travel Hackathon")
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
               (struct item :holiday 1 (t/date-time 2020 7 8) "holiday")))

;; These are Navi's oncall dates
(def oncalls (list
              (struct item :oncall 4 (t/date-time 2020 1 28) "Oncall")
              (struct item :oncall 4 (t/date-time 2020 3 2) "Oncall")
              (struct item :oncall 4 (t/date-time 2020 4 6) "Oncall")
              (struct item :oncall 4 (t/date-time 2020 5 11) "Oncall")
              (struct item :oncall 4 (t/date-time 2020 6 15) "Oncall")
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
             (struct item :inday 1 (t/date-time 2020 12 11) "inday")))

(def tasks (list
            (struct item :work 5 nil "Create Throttling Design Doc")
            (struct item :work 2 nil "Create Nuage Rule with required Configs for size based throttling for expanded companies")
            (struct item :work 3 nil "Implement throttling service skeleton based on response size based throttling for expanded companies")
            (struct item :work 4 nil "Update the expanded companies throttling service to use corresponding nuage fuse rules")
            (struct item :work 4 nil "Update the throttling filter in TRB to use expanded companies throttle service")
            )
  )
            ;; (struct item :work 4 nil "Get API and Security Signoff")
            ;; (struct item :work 6 nil "Add New Offline Spark Job")
            ;; (struct item :work 6 nil "Update TopN")
            ;; (struct item :work 2 nil "Determine the required uplift for pinot and request its increase")
            ;; (struct item :work 6 nil "Load test Pinot")
            ;; (struct item :work 1 nil "Add Data to pinot")
            ;; (struct item :work 3 nil "Verify data in pinot")
            ;; (struct item :work 1 nil "Add new pivot to TRB commons")
            ;; (struct item :work 6 nil "Add pivot logic and tests to TRB")
            ;; (struct item :work 5 nil "Add LIX to TRB")
            ;; (struct item :work 3 nil "Verify API requests")
;;            (struct item :work 2 nil "Load test API")
;;            (struct item :work 2 nil "Ramp TRB LIX by whitelisting new partners ")
;;            (struct item :work 3 nil "DTO")))

(defn add-buffer-to-tasks
  ([tasks] (add-buffer-to-tasks tasks 0 (list)))
  ([tasks point-cumulation] (add-buffer-to-tasks tasks point-cumulation (list)))
  ([tasks point-cumulation buffered-tasks]
   (if (> point-cumulation 15)
     (let [buffer (struct item :buffer 6 (t/date-time 9000 1 1) "Buffer")
           spillover-points (- point-cumulation 16)]
       (add-buffer-to-tasks tasks spillover-points (conj buffered-tasks buffer)))
    ;; else
    ;; process next task
     (if (= (count tasks) 0)
       (reverse buffered-tasks)
       (let [task (peek tasks)
             updated-tasks (pop tasks)
             task-points (:story-points task)
             updated-points (+ task-points point-cumulation)
             update-buffered-tasks (conj buffered-tasks task)]
         (add-buffer-to-tasks updated-tasks updated-points update-buffered-tasks))))))

(def tasks-and-buffer (add-buffer-to-tasks tasks))

(defn update-sprint-task [sprint task]
  (let [start (:start sprint)
        end (:end sprint)
        tasks (conj (:tasks sprint) task) ;; this will need to be tested since conj can feel finicky
        number (:number sprint)
        points-left (:points-left sprint)]
    (struct block start end number tasks points-left)))

(defn update-sprint-storypoints [sprint story-points]
  (let [start (:start sprint)
        end (:end sprint)
        tasks (:tasks sprint) ;; assume the task is added into the sprint elsewhere
        number (:number sprint) ;; not 6, this is the sprint # in the sequence
        points-left (max story-points 0)] ;; ensures we do not get negative story points due to oncall/holiday/travel overlap!!!!!!!
    (struct block start end number tasks points-left)))

(defn put-task-in-sprint [sprint task filled-sprints]
  ;; does not update sprints or tasks list
  (let [story-point-space (:points-left sprint)
        task-points (:story-points task)
        story-points-left-after-completing-task (- story-point-space task-points)
        task-points-left-after-adding-into-sprint (- task-points story-point-space)
        task-fits-into-sprint (> story-points-left-after-completing-task -1)]
    (if task-fits-into-sprint
      ;; return the updated sprint and nil
      (update-sprint-storypoints
       (update-sprint-task sprint task)
       story-points-left-after-completing-task)
      ;; else split the task and return the updated sprint and task
      ())))

(defn get-storypoints-left-after-completing-task [sprint task]
  ;; if sprint is nil, we'll throw an exception, meaning we've run out of sprints to fill 
  (let [story-point-space (:points-left sprint)
        task-points (:story-points task)
        story-points-left-after-completing-task (- story-point-space task-points)]
    story-points-left-after-completing-task))

(defn add-task-to-sprint [sprint task]
  ;; returns a new sprint with updated tasks and storypoints
  (update-sprint-storypoints
   (update-sprint-task sprint task)
   (get-storypoints-left-after-completing-task sprint task)))

(defn copy-task-with-new-storypoints [task points]
  (let [type (:type task)
        story-points points
        date (:date task)
        description (:description task)]
    (struct item type story-points date description)))

(defn update-larger-task [sprint task]
  ;; returns two tasks, split from the original task
  (let [task-points (:story-points task)
        story-point-space (:points-left sprint)
        task-points-left-after-adding-into-sprint (- task-points story-point-space)
        task-to-add (copy-task-with-new-storypoints task story-point-space)
        task-left (copy-task-with-new-storypoints task task-points-left-after-adding-into-sprint)]
    (list task-to-add task-left)))

(defn does-task-fit-in-sprint [sprint task]
  ;; storypoints >= 0, it fits
  ;; > -1, it fits
  (> (get-storypoints-left-after-completing-task sprint task) -1))

(defn fill-sprints
  ([sprints tasks] (fill-sprints sprints tasks (list)))
  ([sprints tasks filled]
  ;; Method will return a list of partially filled sprints
   ;; might throw an exception if we run out of sprints, should not happen for this use
   (if (= (count sprints) 0) (println "Out of sprints" filled) ())
   (if (= (count tasks) 0) ;; base case
     (concat (reverse filled) sprints)
     (let [sprint (peek sprints)
           task (peek tasks)
           story-point-space (:points-left sprint)
           task-fits-in-sprint (does-task-fit-in-sprint sprint task)
           should-remove-sprint (= story-point-space 0) ;; remove sprints with 0 in them
           no-more-tasks (= (count tasks) 0)]
         ;; responsible for popping when a sprint has no more space
       (if should-remove-sprint
         (let [updated-sprints (pop sprints)
               updated-filled (conj filled sprint)]
           (fill-sprints updated-sprints tasks updated-filled))
         (do
           (if task-fits-in-sprint
             (let [updated-sprint (add-task-to-sprint sprint task)
                   updated-tasks (pop tasks)
                   remove-stale-sprint (pop sprints)
                   refresh-sprints (conj remove-stale-sprint updated-sprint)]
               (fill-sprints refresh-sprints updated-tasks filled))
      ;; else
             (let [split-task (update-larger-task sprint task)
                   task-to-add (peek split-task)
                   task-leftover (peek (pop split-task))
                   remove-large-task (pop tasks)
                   add-leftover (conj remove-large-task task-leftover)
                   with-smaller-task (conj add-leftover task-to-add)]
               (fill-sprints sprints with-smaller-task
                             filled)))))))))

(defn is-item-within-sprint [item sprint]
  (let [start (:start sprint)
        end (:end sprint)
        date (:date item)]
    (if (or (and (t/after? date start) (t/before? date end)) (or (t/equal? date start) (t/equal? date end)))
      true
      false)))

(defn add-item-to-sprints
  ([sprints item] (add-item-to-sprints sprints item (list)))
  ([sprints item building]
   (if (= (count sprints) 0)
     (reverse building)
     (let [sprint (peek sprints)]
       (if (is-item-within-sprint item sprint)
         (let [updated-sprint (add-task-to-sprint sprint item)]
           (add-item-to-sprints (pop sprints) item (conj building updated-sprint)))
         (add-item-to-sprints (pop sprints) item (conj building

                                                       sprint)))))))

(defn add-items-to-sprints [items sprints]
  (if (= (count items) 0) sprints
  (let [item (peek items)
        updated-sprints (add-item-to-sprints sprints item)
        ]
    (add-items-to-sprints (pop items) updated-sprints)
    )
  ))

;; I want a way to easily print out a sprint
;; (defstruct block :start :end :number :tasks :points-left) ;; start and end are dates
(defn print-sprint [sprint]
  (let [start (:start sprint)
        end (:end sprint)
        points-left (:points-left sprint)
        ]
    (println "The sprint dates are " start)
    (println end)
    (doall (map #(println %) (:tasks sprint)))
    (println "Points remaining in the sprint: " points-left)
    )
  )

(defn format-date [date]
  (if date
    (do
  (def custom-formatter (f/formatter "MM-dd"))
  (f/unparse custom-formatter date)
  )
    ""
  ))

(defn stack-tasks
  ([tasks] (stack-tasks tasks (list)))
  ([tasks building]
  (if (= 0 (count tasks))
    (clojure.string/join "\n" (reverse building))
    (let [task (peek tasks)
        updated-tasks (pop tasks)
        csv-task (str (:description task) "," (:story-points task) ",Navi," (format-date (:date task)))
        updated-building (conj building csv-task)
        ]
    (stack-tasks updated-tasks updated-building)
    )
    )
  ))

 (defn sprint-to-csv [sprint]
   (let [tasks (stack-tasks (reverse (:tasks sprint)))
         points (:points-left sprint)
         start (:start sprint)
         end (:end sprint)
         ]
     (clojure.string/join " " (list "\n" "Sprint #" (:number sprint) " : " (format-date start) "-" (format-date end) "\n" tasks))
;;     (str "Sprint " (:number sprint) " " start " " end "\n" tasks)
     )
   )

(defn sprints-to-csv [sprints]
  (doall (map sprint-to-csv sprints))
  )

(def with-oncall (add-items-to-sprints oncalls sprints))
(def with-holiday (add-items-to-sprints holidays with-oncall))
(def with-inday (add-items-to-sprints indays with-holiday))
(def with-travel (add-items-to-sprints travels with-inday))
(def scheduled (fill-sprints with-travel tasks-and-buffer))

(defn -main [& args]
;;  (print-sprints scheduled)
  )

