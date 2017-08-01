package com.gcit;
public class ResponseClass {
        String authorName;
        Integer authorId;

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }
        
        public Integer getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Integer authorId) {
            this.authorId = authorId;
        }

        public ResponseClass(String authorName, Integer authorId) {
            this.authorName = authorName;
            this.authorId = authorId;
        }
        
        public ResponseClass(Author a) {
            this.authorName = a.getAuthorName();
            this.authorId = a.getAuthorId();
        }

        public ResponseClass() {
        }

    }