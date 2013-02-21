(ns ccw.hook.leiningenclassloading
  (:require [leiningen.core.classpath :as classpath]
            [leiningen.core.project :as project]
            [clojure.string :as str])
  (:import (org.sonatype.aether.resolution DependencyResolutionException)))

(defn find+download-deps [project-string]
  (let [[_defproject project-name version & {:as args}] (read-string project-string)
        project (project/make args project-name version nil)
        dependencies (:dependencies project)]
    (time 
      (into [] 
            (map #(.getAbsolutePath %)
                 (classpath/resolve-dependencies :dependencies project))))))

(defn spit-deps [deps f]
  (spit f (str/join "\n" deps)))

(defn spit-project-clj [s f]
  (spit f s))
