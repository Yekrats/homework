(ns homework.core
  (:gen-class)
  (:require [clojure.string :as str]))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn parse-line
  "Basic parsing of a line (string) into initial map."
  [string]
  (->> (str/split string #"[\|\s\,]+") ;; parse basic string splitting by the pipe, space, or comma characters
       (zipmap [:last-name :first-name :email :favorite-color :date-of-birth])))

(defn parse-file
  "Parsing each of the lines of a file. 'file-name' is considered a string.
  Returns a map of each line processed through parse-line."
  ; Example:  (parse-file "./resources/comma-data.csv")
  [file-name]
  {:pre [(= (type file-name) java.lang.String)]}
  (->> (slurp file-name)
       (str/split-lines)
       (map parse-line)))
