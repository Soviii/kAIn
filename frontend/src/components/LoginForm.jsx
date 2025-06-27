

export default function LoginForm() {
  return (
<div>
      <span class="input-group-text" id="visible-addon"></span>
      <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="visible-addon" />
      <input type="text" class="form-control d-none" placeholder="Hidden input" aria-label="Hidden input" aria-describedby="visible-addon"></input>
</div>
  );
}