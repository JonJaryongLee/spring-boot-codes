Todo todo = todoRepository.findById(id);
if (todo != null) {
    // 존재할 경우
} else {
    // 존재하지 않을 경우
}

/* =========================== */

Optional<Todo> todo = todoRepository.findById(id)
