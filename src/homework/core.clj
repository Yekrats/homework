(ns homework.core
  (:gen-class)
  (:require [clojure.string :as s]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [compojure.core :refer [defroutes GET POST]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn uuid [& _] (.toString (java.util.UUID/randomUUID)))

(defn parse-date-to-ymd
  "Formats string date M/D/YYYY format to YYYY/MM/DD format (for sorting)"
  [string-date]
  (as-> (s/split string-date #"/") $
       (map #(Integer/parseInt %) $)
       (map #(format "%02d" %) $)
       [(last $) (first $) (second $)]
       (s/join "/" $)))

(defn parse-date-to-mdy
  "Formates string date YYYY/MM/DD to M/D/YYYY format (for viewing)"
  [string-date]
  (as-> (s/split string-date #"/") $
      (map #(Integer/parseInt %) $)
      [(second $) (last $) (first $) ]
      (s/join "/" $))
  )

(defn parse-line
  "Basic parsing of a line (string) into initial map."
  [string]
  (as-> (s/split string #"[\|\s\,]+") $ ;; parse basic string splitting by the pipe, space, or comma characters
      (zipmap [:last-name :first-name :email :favorite-color :date-of-birth] $) ; combine fields with data
      (update-in $ [:date-of-birth] parse-date-to-ymd)
      (update-in $ [:id] uuid)))

(defn parse-files
  "Parsing each of the lines of multiple files. Each 'file-name' is considered
  a string. Returns a map of each line processed through parse-line."
  ; Example:  (parse-files "./resources/comma-data.csv")
  [file-name & more-files]
  {:pre [(= true (or (nil? file-name) (string? file-name)))]}
  (concat
    (when file-name
      (->> (slurp file-name)
        (s/split-lines)
        (map parse-line)))
    (when more-files
      (apply parse-files more-files))))

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

(defn generic-view
  "Step through the records with the 5 points of vital information,
  and print them out to the screen."
  [record-map]
  (as-> record-map $
    (map #(update-in % [:date-of-birth] parse-date-to-mdy) $)
    (map #(select-keys % [:last-name :first-name :email :favorite-color :date-of-birth]) $)
    (doseq [rec $] (println (str "Name: " (:first-name rec) " " (:last-name rec)
                                 ", Email: " (:email rec)
                                 ", Favorite color: " (:favorite-color rec)
                                 ", Date of birth: " (:date-of-birth rec))))))

(defn view-by-email-desc
  "Print record map in email-descending order of the 5 chief keys by means of the generic-view."
  [record-map]
  (-> record-map sort-by-email-desc generic-view))

(defn sort-by-birthdate-asc
  "Sorts a record map by email order descending (then by last name ascending).
   Returns new map in sorted order."
  [record-map]
  (sort-by :date-of-birth record-map)
  )

(defn view-by-birthdate-asc
  "Print record map of birthdate order of the 5 chief keys through the generic view."
  [record-map]
  (-> record-map sort-by-birthdate-asc generic-view))

(defn sort-by-last-name-asc
  "Sorts a record map by email order descending (then by last name ascending).
   Returns new map in sorted order."
  [record-map]
  (sort-by :last-name record-map))

(defn view-by-last-name-asc
  "Print record map of last-name order of the 5 chief keys through the generic view."
  [record-map]
  (-> record-map sort-by-last-name-asc generic-view))

(defn handler [request]
  (response request))

(def json-wrapper
  (wrap-json-response handler))

(def testdata2 '({:last-name "Gruber",
  :first-name "Hans",
  :email "hans@volksfrei.de",
  :favorite-color "blue",
  :date-of-birth "1958/02/14",
  :id "05b6169f-a732-4ced-9e27-bf02a9ccc7f5"}
 {:last-name "Gennero",
  :first-name "Holly",
  :email "gennero@nakatomi.com",
  :favorite-color "yellow",
  :date-of-birth "1960/06/20",
  :id "93ef22cf-8d62-4b6c-b446-fb9cdd507985"}))

(defn email-view-handler [request]
  (response (sort-by-email-desc testdata2)))

(defn birthdate-view-handler [request]
  (response  (sort-by-birthdate-asc testdata2)))

(defn lastname-view-handler [request]
   (response  (sort-by-last-name-asc testdata2)))

(defn post-records-handler [request id]
  (response (filter #(= id (:id %)) testdata2)))
