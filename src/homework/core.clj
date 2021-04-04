(ns homework.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:import [java.text SimpleDateFormat]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn parse-date
  "Formats date to Java date object"
  [string]
    (.parse
      (SimpleDateFormat. "MM/dd/yyyy")
      string))

(defn parse-line
  "Basic parsing of a line (string) into initial map."
  [string]
  (as-> (str/split string #"[\|\s\,]+") $ ;; parse basic string splitting by the pipe, space, or comma characters
      (zipmap [:last-name :first-name :email :favorite-color :date-of-birth] $) ; combine fields with data
      (update-in $ [:date-of-birth] parse-date))
       )

(defn parse-file
  "Parsing each of the lines of a file. 'file-name' is considered a string.
  Returns a map of each line processed through parse-line."
  ; Example:  (parse-file "./resources/comma-data.csv")
  [file-name]
  {:pre [(= true (string? file-name))]}
  (->> (slurp file-name)
       (str/split-lines)
       (map parse-line)))

(defn sort-by-email-desc
  "Sorts a record map by email order descending (then by last name ascending).
   Returns new map in sorted order."
  [record-map]
  (sort-by (juxt :email :last-name)
     (fn [[x0 x1] [y0 y1]]
       (let [c (- (compare x0 y0))]
         (if-not (zero? c)
           c
           (compare x1 y1))))
      record-map))

(defn sort-by-birthdate-asc
  "Sorts a record map by email order descending (then by last name ascending).
   Returns new map in sorted order."
  [record-map]
  (sort-by :date-of-birth record-map)
  )


(defn sort-by-last-name-asc
  "Sorts a record map by email order descending (then by last name ascending).
   Returns new map in sorted order."
  [record-map]
  (sort-by :last-name record-map)
  )
